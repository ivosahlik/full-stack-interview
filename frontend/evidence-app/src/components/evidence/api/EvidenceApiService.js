import { apiClient } from './ApiClient'

export const retrieveAllEvidence
    = () => apiClient.get(`/api/interview/evidences`)

export const deleteEvidenceApi
    = (id) => apiClient.delete(`/api/interview/evidences/${id}`)

export const retrieveEvidenceApi
    = (username, id) => apiClient.get(`/api/interview/evidences/${id}`)

export const updateEvidenceApi
    = (id, evidence) => apiClient.put(`/api/interview/evidences/${id}`, evidence)

export const createEvidenceApi
    = (evidence) => apiClient.post(`/api/interview/evidences`, evidence)
