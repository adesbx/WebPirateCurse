import { defineStore } from 'pinia'

export const useUserStore = defineStore('resources', () => {
  state: () => {
    return {
      resources: [],
    }
  }
})
