package com.scarlettjoubert.noteswithcomments.hilt

import android.content.Context
import androidx.room.Room
import com.scarlettjoubert.noteswithcomments.data.CommentsRepositoryImpl
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsRoomDataBase
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesRoomDataBase
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Singleton
    @Provides
    fun getNoteDataBase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        NotesRoomDataBase::class.java,
        "notes"
    ).allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NotesRoomDataBase) = db.notesDao()

    @Singleton
    @Provides
    fun getCommentsDataBase(
        @ApplicationContext app: Context,
    ) = Room.databaseBuilder(
        app,
        CommentsRoomDataBase::class.java,
        "comments"
    ).allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideCommentsDao(db: CommentsRoomDataBase) = db.commentsDao()

    @Singleton
    @Provides
    fun provideRepository(repositoryData: NotesRepository): NotesRepositoryInterface =
        repositoryData

    @Singleton
    @Provides
    fun provideCommentRepository(repositoryData: CommentsRepositoryImpl): CommentRepository =
        repositoryData
}