import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import Builder from './views/Builder.vue';
import Statistic from './views/Statistic.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Главная',
      component: Home,
    },
    {
      path: '/process',
      name: 'Бизнес-процесс (гос услуга)',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Process.vue'),
    },
    {
      path: '/builder',
      name: 'Конструктор формы',
      component: Builder,
    },
    {
      path: '/statistic',
      name: 'Статистика',
      component: Statistic,
    },
  ],
});
