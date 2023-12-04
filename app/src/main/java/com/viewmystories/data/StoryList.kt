package com.viewmystories.data

import java.io.Serializable

data class StoryList(
    val id: Int,
    val title: String,
    val image: String,
    val video: String,
    val isImage: Boolean,
): Serializable

data class PostList(
    val id: Int,
    val title: String,
    val image: String
): Serializable