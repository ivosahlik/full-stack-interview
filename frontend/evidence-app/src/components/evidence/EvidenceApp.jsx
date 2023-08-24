import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom'
import LogoutComponent from './LogoutComponent'
import HeaderComponent from './HeaderComponent'
import ListEvidencesComponent from './ListEvidencesComponent'
import ErrorComponent from './ErrorComponent'
import LoginComponent from './LoginComponent'
import EvidenceComponent from './EvidenceComponent'
import AuthProvider, { useAuth } from './security/AuthContext'

import './EvidenceApp.css'

function AuthenticatedRoute({children}) {
    const authContext = useAuth()
    
    if(authContext.isAuthenticated)
        return children

    return <Navigate to="/" />
}

export default function EvidenceApp() {
    return (
        <div className="EvidenceApp">
            <AuthProvider>
                <BrowserRouter>
                    <HeaderComponent />
                    <Routes>
                        <Route path='/' element={ <LoginComponent /> } />
                        <Route path='/login' element={ <LoginComponent /> } />
                        
                        <Route path='/evidences' element={
                            <AuthenticatedRoute>
                                <ListEvidencesComponent />
                            </AuthenticatedRoute>
                        } />

                        <Route path='/evidence/:id' element={
                            <AuthenticatedRoute>
                                <EvidenceComponent />
                            </AuthenticatedRoute>
                        } />
  

                        <Route path='/logout' element={
                            <AuthenticatedRoute>
                                <LogoutComponent /> 
                            </AuthenticatedRoute>
                        } />
                        
                        <Route path='*' element={<ErrorComponent /> } />

                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </div>
    )
}
