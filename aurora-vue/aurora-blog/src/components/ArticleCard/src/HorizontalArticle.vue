<template>
  <div class="article-container">
    <span v-if="article.isTop" class="article-tag">
      <b>
        <svg-icon icon-class="pin" />
        {{ t('settings.pinned') }}
      </b>
    </span>
    <span v-else-if="article.isfeatured" class="article-tag">
      <b>
        <svg-icon icon-class="hot" />
        {{ t('settings.featured') }}
      </b>
    </span>
    <div class="feature-article">
      <div class="feature-thumbnail">
        <img v-if="article.articleCover" class="ob-hz-thumbnail" v-lazy="article.articleCover" />
        <img v-else class="ob-hz-thumbnail" src="@/assets/default-cover.jpg" />
        <span class="thumbnail-screen" :style="bannerHoverGradient" />
      </div>
      <div class="feature-content">
        <span>
          <b v-if="article.categoryName">
            {{ article.categoryName }}
          </b>
          <ob-skeleton v-else tag="b" height="20px" width="35px" />

          <ul>
            <template v-if="article.tags && article.tags.length > 0">
              <li v-for="tag in article.tags" :key="tag.id">
                <em># {{ tag.tagName }}</em>
              </li>
            </template>
            <template v-else-if="article.tags && article.tags.length <= 0">
              <li>
                <em># {{ t('settings.default-tag') }}</em>
              </li>
            </template>
            <ob-skeleton v-else :count="2" tag="li" height="16px" width="35px" />
          </ul>
        </span>

        <router-link v-if="article.articleTitle" :to="'/articles/' + article.id">
          <h1 data-dia="article-link">{{ article.articleTitle }}</h1>
        </router-link>
        <ob-skeleton v-else tag="h1" height="3rem" />

        <p v-if="article.articleContent">{{ article.articleContent }}</p>
        <ob-skeleton v-else tag="p" :count="3" height="20px" />

        <div class="article-footer" v-if="article">
          <div class="flex flex-row items-center">
            <img
              class="hover:opacity-50 cursor-pointer"
              v-lazy="article.author.avatar"
              alt=""
              @click="handleAuthorClick(article.author.website)" />
            <span class="text-ob-dim">
              <strong
                class="text-ob-normal pr-1.5 hover:text-ob hover:opacity-50 cursor-pointer"
                @click="handleAuthorClick(article.author.website)">
                {{ article.author.nickname }}
              </strong>
              {{ t('settings.shared-on') }} {{ t(`settings.months[${new Date(article.createTime).getMonth()}]`) }}
              {{ new Date(article.createTime).getDate() }}, {{ new Date(article.createTime).getFullYear() }}
            </span>
          </div>
        </div>

        <div class="article-footer" v-else>
          <div class="flex flex-row items-center mt-6">
            <ob-skeleton class="mr-2" height="28px" width="28px" :circle="true" />
            <span class="text-ob-dim mt-1">
              <ob-skeleton height="20px" width="150px" />
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, toRef } from 'vue'
import { useAppStore } from '@/stores/app'
import { useArticleStore } from '@/stores/article'
import { useI18n } from 'vue-i18n'

export default defineComponent({
  name: 'HorizontalArticle',
  setup() {
    const appStore = useAppStore()
    const articleStore = useArticleStore()
    const { t } = useI18n()
    const handleAuthorClick = (link: string) => {
      if (link === '') link = window.location.href
      window.location.href = link
    }

    return {
      bannerHoverGradient: computed(() => {
        return { background: appStore.themeConfig.header_gradient_css }
      }),
      article: toRef(articleStore.$state, 'topArticle'),
      handleAuthorClick,
      t
    }
  }
})
</script>
