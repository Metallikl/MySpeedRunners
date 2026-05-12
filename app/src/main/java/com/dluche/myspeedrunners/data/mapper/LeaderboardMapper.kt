package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.leaderboard.LeaderboardDto
import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.leaderboard.Leaderboard
import com.dluche.myspeedrunners.domain.model.leaderboard.LeaderboardRun
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.extension.orRandomId

fun LeaderboardDto?.asDomainModel(): Leaderboard {
    return this?.let{
        val category = it.category?.data.asDomainModel()
        val game = it.game?.data.asDomainModel()
        return Leaderboard(
            weblink = it.weblink.orEmpty(),
            game = game,
            category = category,
            timing = it.timing.orEmpty(),
            runs = it.handleRuns(category),
            platforms = it.platforms?.data.asDomainModel()
        )
    }?: getEmptyLeaderboard()


}

private fun LeaderboardDto.handleRuns(category: Category): List<LeaderboardRun> {
    return this.runs?.map {
        val run = it.run?.asDomainModel() ?: getEmptyRun()
        LeaderboardRun(
            place = it.place.orRandomId(),
            category = category,
            date = run.date,
            id = run.id,
            submitted = run.submitted,
            status = run.status,
            primaryTime = run.primaryTime,
            runner = findMainRunner(run)

        )
    } ?: emptyList()
}

private fun LeaderboardDto.findMainRunner(run: Run): RunnerCard =
    this.players?.wrapper?.find { it.id == run.runners[0].id }?.asCardDomainModel()
        ?: getEmptyRunnerCard()

fun getEmptyLeaderboard() = Leaderboard(
    weblink = "",
    game = getEmptyGame(),
    category = getEmptyCategory(),
    timing = "",
    runs = emptyList(),
    platforms = emptyList()
)

