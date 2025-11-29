package com.dluche.myspeedrunners.extension

import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

fun Runner.toRunnerCard() = RunnerCard(
    id = this.id,
    name = this.name,
    pronouns = this.pronouns,
    japaneseName = this.japaneseName,
    nameStyle = this.nameStyle,
    location = this.location,
    locationUrl = this.locationUrl,
    imageUrl = this.imageUrl,
    role = this.role,
    signup = this.signup
)
