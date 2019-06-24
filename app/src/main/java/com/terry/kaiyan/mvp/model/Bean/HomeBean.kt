package com.terry.kaiyan.mvp.model.Bean

/**
 * 作者：Terry.CHen
 * 创建日期：2019/06/22
 * 邮箱：herewinner@163.com
 * 描述：TODO
 */
data class HomeBean(
    val issueList: ArrayList<Issue>,
    val nextPageUrl: String,
    val nextPublishTime: Long,
    val newestIssueType: String
) {
    data class Issue(
        val releaseTime: Long,
        val type: String,
        val date: Long,
        val publishTime: Long,
        val itemList: ArrayList<HomeItem>,
        val count: Int
    ) {
        data class HomeItem(val type: String, val data: HomeData, val tag: Any, val id: Int, val adIndex: Int) {
            data class HomeData(
                val dataType: String,
                val id: Long,
                val title: String,
                val description: String,
                val library: String,
                val tags: ArrayList<HomeTag>,
                val consumption: HomeConsumption,
                val resourceType: String,
                val slogan: String,
                val provider: HomeProvider,
                val category: String,
                val author: HomeAuthor,
                val cover: HomeCover,
                val playUrl: String,
                val thumbPlayUrl: String,
                val duration: Long,
                val webUrl: HomeWebUrl,
                val releaseTime: Long,
                val playInfo: ArrayList<HomePlayInfo>,
                val date: Long,
                val descriptionEditor: String,
                val collected: Boolean,
                val played: Boolean
            ){
                data class HomeTag(
                    val id: Int,
                    val name: String,
                    val actionUrl: String,
                    val desc: String,
                    val bgPicture: String,
                    val headerImage: String,
                    val tagRecType: String,
                    val communityIndex: Int

                )
                data class HomeConsumption(val collectionCount: Int, val shareCount: Int, val replyCount: Int)
                data class HomeProvider(val name:String, val alias:String, val icon:String)
                data class HomeAuthor(
                    val id: Int,
                    val icon: String,
                    val name: String,
                    val description: String,
                    val latestReleaseTime: Long,
                    val videoNum: Int,
                    val follow: HomeFollow,
                    val shield: HomeShield,
                    val approvedNotReadyVideoCount: Int,
                    val ifPgc: Boolean,
                    val recSort: Int,
                    val expert: Boolean
                ) {
                    data class HomeFollow(val itemType: String, val itemId: Int, val followed: Boolean)
                    data class HomeShield(val itemType:String, val itemId: Int, val shielded: Boolean)

                }
                data class HomeCover(val feed: String, val detail: String, val blurred: String, val homepage: String)
                data class HomeWebUrl(val raw:String, val forWeibo:String)
                data class HomePlayInfo(
                    val height: Int,
                    val width: Int,
                    val name: String,
                    val type: String,
                    val url: String,
                    val urlList: ArrayList<HomeUrl>
                ){
                    data class HomeUrl(val name: String, val url: String, val size: Long)
                }
            }
        }
    }
}
