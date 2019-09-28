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
