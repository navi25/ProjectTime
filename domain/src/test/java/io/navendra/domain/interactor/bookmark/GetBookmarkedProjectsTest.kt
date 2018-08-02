package io.navendra.domain.interactor.bookmark

import com.nhaarman.mockito_kotlin.whenever
import io.navendra.domain.executor.PostExecutionThread
import io.navendra.domain.model.Project
import io.navendra.domain.repository.ProjectsRepository
import io.navendra.domain.test.ProjectDataFactory
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest{

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects

    @Mock lateinit var projectsRepository : ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository,postExecutionThread)
    }

    @Test
    fun getBookmarkedProjectsCompletes(){
        stubGetBookmarkedProjects(Observable.just(ProjectDataFactory.makeProjectList(10)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()

    }

    @Test
    fun getBookmarkedProjectsReturnsData(){
        val projects = ProjectDataFactory.makeProjectList(10)
        stubGetBookmarkedProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<Project>>){
        whenever(projectsRepository.getBookmarkedProjects())
                .thenReturn(observable)
    }
}