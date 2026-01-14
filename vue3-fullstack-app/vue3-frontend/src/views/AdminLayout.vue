<script setup>
import { storeToRefs } from "pinia";
import { useUiStore } from "@/stores/uiStore";

// 取得 UI 相關的 Store
const uiStore = useUiStore();
// 使用 storeToRefs 將 state 解構為響應式參考 (ref)，這樣解構後才不會失去響應性
// drawer: 控制側邊選單開關
// adminMenu: 後台選單項目列表
const { drawer, adminMenu } = storeToRefs(uiStore);
</script>

<template>
  <!-- v-layout 是 Vuetify 的佈局容器，用來自動排列導航列、側邊欄與主要內容 -->
  <v-layout>
    <!-- 側邊導航欄 (Navigation Drawer) -->
    <!-- v-model="drawer" 控制顯示/隱藏 -->
    <v-navigation-drawer v-model="drawer">
      <!-- 選單列表 -->
      <!-- :items="adminMenu" 自動渲染選單項目 -->
      <!-- item-props 自動將 items 中的屬性 (title, prepend-icon, to) 綁定到 v-list-item -->
      <v-list density="compact" item-props :items="adminMenu" nav />

      <template #append>
        <!-- 底部固定項目 (例如設定) -->
        <v-list-item
          class="ma-2"
          link
          nav
          prepend-icon="mdi-cog-outline"
          title="Settings"
        />
      </template>
    </v-navigation-drawer>

    <!-- 頂部應用程式列 (App Bar) -->
    <v-app-bar border="b" class="ps-4" flat>
      <!-- 漢堡選單按鈕，點擊切換側邊欄顯示狀態 -->
      <!-- smAndDown: 在小螢幕時才顯示 -->
      <v-app-bar-nav-icon
        v-if="$vuetify.display.smAndDown"
        @click="drawer = !drawer"
      />

      <v-app-bar-title>後台管理系統</v-app-bar-title>

      <template #append>
        <!-- 使用者頭像與下拉選單 -->
        <v-btn class="text-none me-2" height="48" icon slim>
          <v-avatar
            color="surface-light"
            image="https://cdn.vuetifyjs.com/images/john.png"
            size="32"
          />

          <!-- 下拉選單內容 -->
          <v-menu activator="parent">
            <v-list density="compact" nav>
              <v-list-item
                append-icon="mdi-cog-outline"
                link
                title="Settings"
              />
              <v-list-item append-icon="mdi-logout" link title="Logout" />
            </v-list>
          </v-menu>
        </v-btn>
      </template>
    </v-app-bar>

    <!-- 主要內容區域 (Main Content) -->
    <v-main>
      <div class="pa-4">
        <!-- 這裡是子路由渲染的地方 -->
        <!-- 例如點擊 "會員管理" 時，MemberManager.vue 會顯示在這裡 -->
        <router-view />
      </div>
    </v-main>
  </v-layout>
</template>
