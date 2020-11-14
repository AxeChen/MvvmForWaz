package com.mg.axechen.wanandroid.ui.home

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mg.axechen.libcommon.DensityUtil
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.BannerBean
import com.mg.axechen.wanandroid.model.ProjectBean
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerAdapter

class HomeCardAdapter(
    private var activity: FragmentActivity,
    views: MutableList<HomeCardViewType>
) :
    BaseMultiItemQuickAdapter<HomeCardViewType, BaseViewHolder>(views) {

    var homeItemClickListener: HomeItemClickListener? = null

    init {
        addItemType(HomeCardViewType.VIEW_CARD_ABOUT_APP, R.layout.item_home_card_about_app)
        addItemType(HomeCardViewType.VIEW_TYPE_HOT_ARTICLE, R.layout.item_home_card_hot_article)
        addItemType(HomeCardViewType.VIEW_TYPE_BANNER, R.layout.item_home_card_banner)
        addItemType(HomeCardViewType.VIEW_TYPE_HOT_PROJECT, R.layout.item_home_card_project_card)
        addItemType(HomeCardViewType.VIEW_CARD_DEVELOPER, R.layout.item_home_card_about_author)
        addItemType(HomeCardViewType.VIEW_CARD_SIGN, R.layout.item_home_card_sgin)
        addChildClickViewIds(R.id.rtToMore, R.id.rtToAuthor, R.id.ivCardClose, R.id.tvMoreArticle,R.id.tvToMoreProject)
    }


    override fun convert(holder: BaseViewHolder, item: HomeCardViewType) {
        if (item.itemType == HomeCardViewType.VIEW_TYPE_HOT_ARTICLE) {
            // 热门文章
            val articles = item.item as MutableList<ArticleBean>
            val recyclerView = holder.getView<RecyclerView>(R.id.itemCardList)
            recyclerView.run {
                layoutManager = LinearLayoutManager(recyclerView.context)
                var articleAdapter = HotArticleAdapter(articles)
                adapter = articleAdapter
                isNestedScrollingEnabled = false
                articleAdapter.setOnItemClickListener { adapter, view, position ->
                    // 跳转到WebView
                    homeItemClickListener?.run {
                        articleClickListener(articles[position])
                    }
                }
            }
        } else if (item.itemType == HomeCardViewType.VIEW_TYPE_BANNER) {
            // banner
            var bannerList = item.item as MutableList<BannerBean>
            var adapter = HomeBannerAdapter(context, bannerList)
            var banner = holder.getView<Banner<BannerBean, HomeBannerAdapter>>(R.id.bannerArticle)
            banner.addBannerLifecycleObserver(activity)
                .setAdapter(adapter)
                .start()

        } else if (item.itemType == HomeCardViewType.VIEW_TYPE_HOT_PROJECT) {
            var projectList = item.item as MutableList<ArticleBean>
            var projectAdapter = HotProjectAdapter(projectList)
            var recyclerView = holder.getView<RecyclerView>(R.id.itemProjectList)
            recyclerView.run {
                layoutManager = GridLayoutManager(context, 2)
                isNestedScrollingEnabled = false
                adapter = projectAdapter
                projectAdapter.setOnItemClickListener { adapter, view, position ->
                    homeItemClickListener?.run {
                        articleClickListener(projectList[position])
                    }
                }
            }

        }

    }

    private class HomeBannerAdapter(private var context: Context, data: MutableList<BannerBean>) :
        BannerAdapter<BannerBean, HomeBannerAdapter.BannerViewHolder>(data) {

        private var layoutInflater: LayoutInflater? = null

        init {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        }

        class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var imageView: ImageView? = null
            var title: TextView? = null

            init {
                imageView = view.findViewById(R.id.bannerImage)
                title = view.findViewById(R.id.bannerTitle)
            }
        }

        override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
            var view = layoutInflater!!.inflate(R.layout.item_home_banner, null, false)
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            return BannerViewHolder(view)
        }

        override fun onBindView(
            holder: BannerViewHolder?,
            data: BannerBean?,
            position: Int,
            size: Int
        ) {
            data?.run {
                Glide.with(context).load(imagePath).into(holder!!.imageView!!)
                holder.title!!.setText(title)
            }
        }

    }

    private class HotProjectAdapter(data: MutableList<ArticleBean>) :
        BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.item_hot_project, data) {


        override fun convert(holder: BaseViewHolder, item: ArticleBean) {
            holder.setText(R.id.tvProjectTitle, item.title)
            var projectImage = holder.getView<ImageView>(R.id.ivProjectImage)
            var imageWidth = (DensityUtil.getWindowWidth(projectImage.context) - DensityUtil.dip2px(
                projectImage.context,
                64f
            )) / 2
            var lp = projectImage.layoutParams
            lp.width = imageWidth
            lp.height = imageWidth / 2

            projectImage.layoutParams = lp
            Glide.with(context).load(item.envelopePic).into(projectImage)
        }

    }

    private class HotArticleAdapter(article: MutableList<ArticleBean>) :
        BaseQuickAdapter<ArticleBean, BaseViewHolder>(
            R.layout.item_home_hot_article_no_image,
            article
        ) {
        override fun convert(holder: BaseViewHolder, item: ArticleBean) {
            holder.setText(R.id.tvHotArticleTitle, item.title)
            holder.setText(R.id.tvHotArticleTime, item.niceDate)
        }

    }


    interface HomeItemClickListener {
        fun articleClickListener(articleBean: ArticleBean)
    }
}