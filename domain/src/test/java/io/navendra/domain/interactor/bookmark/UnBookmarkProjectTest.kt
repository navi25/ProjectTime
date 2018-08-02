package io.navendra.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.navendra.domain.executor.PostExecutionThread
import io.navendra.domain.repository.ProjectsRepository
import io.navendra.domain.test.ProjectDataFactory
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnBookmarkProjectTest {

    private lateinit var unBookmarkProject: UnBookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        unBookmarkProject = UnBookmarkProject(projectsRepository,postExecutionThread)
    }

    @Test
    fun bookmarkProjectCompletes(){
        stubUnBookmarkProject(Completable.complete())
        val testObserver = unBookmarkProject.buildUseCaseCompletable(
                UnBookmarkProject.Params.forProject(ProjectDataFactory.randomUUID())
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unBookmarkProjectThrowsException(){
        unBookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubUnBookmarkProject(completable: Completable){
        whenever(projectsRepository.unBookmarkProject(any()))
                .thenReturn(completable)
    }
}