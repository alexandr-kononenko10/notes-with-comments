package com.scarlettjoubert.noteswithcomments.data

import androidx.annotation.WorkerThread
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDao
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import com.scarlettjoubert.noteswithcomments.data.mapper.NotesMapper
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.Topic
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesDao: NotesDao,
    private val mapper: NotesMapper,
) : NotesRepositoryInterface {

    private val repositoryScope = CoroutineScope(Dispatchers.Default)

    val notes: Flow<List<NotesDto>> = notesDao.getAllNotesDesc()

    val topics: Flow<List<String>> = notesDao.getTopics()

    private val _savedTopic = MutableStateFlow(INITIAL_SAVED_TOPIC)
    private val savedTopic = _savedTopic
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Lazily,
            initialValue = INITIAL_SAVED_TOPIC
        )
    private val notesFromTopicStateFlow = MutableStateFlow<List<Note>>(listOf())
    fun getNotesFromTopic(topic: String): List<NotesDto> = notesDao.geListNotesFromTopicAsc(topic)

    fun getNoteById(id: Int): List<NotesDto> = notesDao.getNoteByID(id)

    override fun getListOfNotesDesc(): StateFlow<List<Note>> {
        return notesDao.getAllNotesDesc()
            .map { mapper.mapListOfNotesDtoToNote(it) }
            .stateIn(
                scope = repositoryScope,
                started = SharingStarted.Lazily,
                initialValue = listOf()
            )
    }

    override fun getAllNotesList(): List<Note> {
        return notesDao.getAllNotesListDesc().map {
            mapper.mapNotesDtoToNote(it)
        }
    }

    override fun getListOfTopics(): StateFlow<List<Topic>> {
        return notesDao.getTopics()
            .map { topicList ->
                topicList.toSet().withIndex().map { item ->
                    Topic(
                        id = item.index,
                        name = item.value,
                        count = notesDao.geListNotesFromTopicAsc(item.value).size
                    )
                }
            }.stateIn(
                scope = repositoryScope,
                started = SharingStarted.Lazily,
                initialValue = listOf()
            )
    }

    override fun createNote(text: String, topic: String) {
        val noteDto = NotesDto(
            id = null,
            text = text,
            topic = topic,
            created = Calendar.getInstance().time.time
        )
        notesDao.insert(noteDto)
    }

    override fun getListOfNotesFromTopic(topic: String): StateFlow<List<Note>> {
        return notesDao.geNotesFromTopicAsc(topic)
            .map { mapper.mapListOfNotesDtoToNote(it) }
            .stateIn(
                scope = repositoryScope,
                started = SharingStarted.Lazily,
                initialValue = listOf()
            )
    }

    override fun updateNote(note: Note) {
        notesDao.updateNote(
            topic = note.topic,
            text = note.text,
            id = note.id ?: throw IllegalArgumentException()
        )
    }

    override fun deleteNote(note: Note) {
        notesDao.deleteNotes(note.id ?: throw IllegalArgumentException())
    }

    override fun searchNote(query: String): StateFlow<List<Note>> {
        return notesDao.search(query).map { list ->
            list.map {
                mapper.mapNotesDtoToNote(it)
            }
        }.stateIn(
                scope = repositoryScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )
    }

    override fun saveNte(note: Note) {
        val noteDto = mapper.mapNoteToToNoteDto(note)
        notesDao.insert(noteDto)
    }

    fun getFlowNotesFromTopic(topic: String): Flow<List<NotesDto>> =
        notesDao.geFlowNotesFromTopicAsc(topic)

    fun insert(note: NotesDto) = notesDao.insert(note)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        notesDao.deleteAll()
    }

    fun update(note: NotesDto) =
        notesDao.updateNote(topic = note.topic, text = note.text, id = note.id!!)

    fun delete(id: Int) = notesDao.deleteNotes(id)

    fun search(query: String): Flow<List<NotesDto>> = notesDao.search(query)

    companion object {
        private const val INITIAL_SAVED_TOPIC = ""
    }
}