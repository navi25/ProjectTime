package io.navendra.domain.interactor.bookmark

import io.navendra.domain.executor.PostExecutionThread
import io.navendra.domain.interactor.ObservableUseCase
import io.navendra.domain.model.Project
import io.navendra.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetBookmarkedProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository, postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Project>,Nothing>(postExecutionThread){

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getBookmarkedProjects()
    }

}