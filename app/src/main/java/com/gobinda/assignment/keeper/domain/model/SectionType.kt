package com.gobinda.assignment.keeper.domain.model

import com.google.gson.annotations.SerializedName

enum class SectionType {
    @SerializedName("banner")
    BANNER,
    @SerializedName("horizontalFreeScroll")
    HORIZONTAL_FREE_SCROLL,
    @SerializedName("splitBanner")
    SPLIT_BANNER

}