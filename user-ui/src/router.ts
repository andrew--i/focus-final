import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import Response from './views/Response.vue';
import Form from './views/Form.vue'

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Home',
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
      path: '/form',
      name: 'Form',
      component: Form
    },
    {
      path: '/response',
      name: 'response',
      component: Response,
    }
  ],
});
