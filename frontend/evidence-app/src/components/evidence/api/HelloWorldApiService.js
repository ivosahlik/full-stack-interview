import { apiClient } from './ApiClient'

export const retrieveHelloWorldPathVariable
    = (username) => apiClient.get(`/api/interview/hello-interview`)

