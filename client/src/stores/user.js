import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  state: () => {
    return {
      login: '',
      connected: false,
      img: null,
      position: null,
      isDead: false,
      role: '',
      direction: ''
    }
  }
})
