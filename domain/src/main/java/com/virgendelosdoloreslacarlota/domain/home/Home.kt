package com.virgendelosdoloreslacarlota.domain.home

import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.sponsor.Sponsor

data class Home(
    val sponsors: List<Sponsor>,
    val mass: List<Mass>,
    val burial: List<Burial>,
    val news: List<News>
)