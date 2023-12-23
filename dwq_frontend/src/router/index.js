import { createRouter, createWebHistory } from 'vue-router'
import { unauthorized } from "@/net";
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                }, {
                    path: 'register',
                    name: 'welcome-register',
                    component: () => import('@/views/welcome/RegisterPage.vue')
                }, {
                    path: 'forget',
                    name: 'welcome-forget',
                    component: () => import('@/views/welcome/ForgetPage.vue')
                }
            ]
        },
        {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue'),
        },
        {
            path: '/admin',
            name: 'admin',
            component: () => import('@/views/admin/AdminLayout.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-index',
                    component: () => import('@/views/admin/IndexManager.vue')
                }
                ,
                {
                    path: 'admin',
                    name: 'welcome-admin',
                    component: () => import('@/views/admin/AdminManager.vue')
                },
                {
                    path: 'address',
                    name: 'admin-address',
                    component: () => import('@/views/admin/AddressManager.vue')
                },
                {
                    path: 'ai',
                    name: 'admin-ai',
                    component: () => import('@/views/admin/AiManager.vue')
                },
                {
                    path: 'order-detail',
                    name: 'admin-order-detail',
                    component: () => import('@/views/admin/OrderDetailManager.vue')
                },
                {
                    path: 'orders',
                    name: 'admin-orders',
                    component: () => import('@/views/admin/OrdersManager.vue')
                },
                {
                    path: 'pet',
                    name: 'admin-pet',
                    component: () => import('@/views/admin/PetManager.vue')
                },
                {
                    path: 'pet-kind',
                    name: 'admin-pet-kind',
                    component: () => import('@/views/admin/PetKindManager.vue')
                },
                {
                    path: 'review',
                    name: 'admin-review',
                    component: () => import('@/views/admin/ReviewManager.vue')
                },
                {
                    path: 'shopping',
                    name: 'admin-shopping',
                    component: () => import('@/views/admin/ShoppingManager.vue')
                },
                {
                    path: 'center',
                    name: 'admin-center',
                    component:()=>import('@/views/user/CenterManager.vue')
                },
                {
                    path: 'rest',
                    name: 'admin-rest',
                    component:()=>import('@/views/user/RestManager.vue')
                }
            ]
        },
        {
            path: '/user',
            name: 'user',
            component: () => import('@/views/user/IndexUser.vue'),
            children:[
                {
                    path: 'center',
                    name: 'user-center',
                    component:()=>import('@/views/user/CenterManager.vue')
                },
                {
                    path: 'rest',
                    name: 'user-rest',
                    component:()=>import('@/views/user/RestManager.vue')
                },
                {
                    path: 'ai',
                    name: 'user-rest',
                    component:()=>import('@/views/user/AiManager.vue')
                },
                {
                    path: 'shopping',
                    name: 'user-shopping',
                    component:()=>import('@/views/user/ShoppingManager.vue')
                },
                {
                    path: 'order-detail',
                    name: 'user-order-detail',
                    component:()=>import('@/views/user/OrderDetailManager.vue')
                },




            ]
        }
    ]
})

router.beforeEach((to, from, next) => {
    try {
        const isUnauthorized = unauthorized(); // 检查用户是否未登录
        const publicPages = ['welcome-login', 'welcome-register', 'welcome-forget']; // 公开页面列表
        const isPublicPage = publicPages.includes(to.name); // 检查即将访问的路由是否在公开页面列表中

        // 用户已登录且试图访问公开页面之一，重定向到首页
        if (!isUnauthorized && isPublicPage) {
            next('/user');
            return;
        }
        // 用户未登录且试图访问非公开页面，重定向到登录页面
        if (isUnauthorized && !isPublicPage) {
            next({ name: 'welcome-login', query: { message: '请先登录' } });
            return;
        }
        next();
    } catch (error) {
        console.error('Routing error:', error);
        next(false); // 停止当前的导航
    }
});

export default router;
