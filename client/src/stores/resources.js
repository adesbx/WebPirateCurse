import { defineStore } from 'pinia'

export const useResourcesStore = defineStore('resources', () => {
  state: () => {
    return {
      resources: [],
      positionNE: [],
      positionSO: []
    }
  }
})
