package com.dluche.myspeedrunners.ui.fake

import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum

val run1 = Run(
    id = "1",
    weblink = "https://www.speedrun.com/run/1",
    comment = "No comments",
    date="22/01/1988",
    game = game1,
    category = Category(
        id = "1",
        name = "Category 1",
        weblink = "https://www.speedrun.com/category/1",
        links= emptyList(),
        miscellaneous = false,
        rules= "Lot of rules",
        type = "Some type",
    ),
    links = emptyList(),
    splits = null,
    submitted = "25/01/2025",
    videos = emptyList(),
    status = RunStatusEnum.VERIFIED,
    primaryTime = "PT26M43S"
)

val run2 = Run(
    id = "2",
    weblink = "https://www.speedrun.com/run/1",
    comment = "",
    date="05/09/2024",
    game = game2,
    category = Category(
        id = "1",
        name = "Category 2",
        weblink = "https://www.speedrun.com/category/1",
        links= emptyList(),
        miscellaneous = false,
        rules= "Lot of rules",
        type = "Some type",
    ),
    links = emptyList(),
    splits = null,
    submitted = "25/10/2024",
    videos = emptyList(),
    status = RunStatusEnum.NEW,
    primaryTime =  "PT14H28M35S"
)