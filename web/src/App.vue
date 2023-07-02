<template>
    <div class="common-layout">
        <el-container>
            <el-header class="navbar">
                <NavBar/>
            </el-header>
            <el-container>
                <el-aside class="sidebar">
                    <SideBar/>
                </el-aside>
                <el-main class="main">
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>


<script>

import NavBar from "@/components/basic/NavBar";
import SideBar from "@/components/basic/SideBar";
import "@/assets/font/font.css";
export default {
  name: 'app',
  components: {
      SideBar,
      NavBar,
  }
}
</script>

<style>
#app {
    font-family: Yantian;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
}
.navbar {
    height: 4rem;
    --el-header-padding: 0 0;
}
.sidebar {
    width: 14rem;
    height: calc(100vh - 6rem);
    margin-left: 0rem;
}
.main {
    position: absolute;
    left: 14rem;
    right: 0;
    top: 4rem;
    bottom: 0;
    overflow-y: scroll;
    /*上右下左*/
    padding: 1rem 10vw 1rem 3rem;
}
</style>
