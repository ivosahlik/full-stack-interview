import {useEffect, useState} from "react"
import {useNavigate} from 'react-router-dom'
import {deleteEvidenceApi, retrieveAllEvidence} from "./api/EvidenceApiService"

function ListEvidencesComponent() {

    const navigate = useNavigate()

    const [evidences, setEvidences] = useState([])

    const [message, setMessage] = useState(null)

    const [name, setName] = useState('');

    const [foundEvidences, setFoundEvidences] = useState([]);

    useEffect(() => refreshEvidences(), [])

    function refreshEvidences() {

        retrieveAllEvidence()
            .then(response => {
                setEvidences(response.data)
                setFoundEvidences(response.data)
            })
            .catch(error => console.log(error))

    }

    function deleteEvidence(id) {
        deleteEvidenceApi(id)
            .then(
                () => {
                    setMessage(`Delete of evidences with id = ${id} successful`)
                    refreshEvidences()
                }
            )
            .catch(error => console.log(error))
    }

    function updateEvidence(id) {
        navigate(`/evidence/${id}`)
    }

    function addNewEvidence() {
        navigate(`/evidence/-1`)
    }

    function showVersion(version) {

        return (
            <div>
                {
                    version.map(version => {
                        return <span className="badge bg-secondary" style={{margin: 5 + 'px'}}>{version.version}</span>
                    })
                }
            </div>
        );
    }

    const filter = (e) => {
        const keyword = e.target.value;
        if (keyword !== '') {
            const results = evidences.filter((evidence) => {
                return evidence.name.toLowerCase().startsWith(keyword.toLowerCase());
            });
            setFoundEvidences(results);
        } else {
            setFoundEvidences(evidences);
        }
        setName(keyword);
    };

    return (
        <div className="container">
            <h1>Frameworks Here!</h1>

            {message && <div className="alert alert-warning">{message}</div>}

            <input
                type="search"
                value={name}
                onChange={filter}
                className="input"
                placeholder="Filter"
            />

            <div>
                <table className="table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Deprecation Date</th>
                        <th>Version</th>
                        <th>Hype Level</th>
                        <th>Delete</th>
                        <th>Update</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        foundEvidences && foundEvidences.length > 0 ? (
                            foundEvidences.map(
                                evidence => (
                                    <tr key={evidence.id}>
                                        <td>{evidence.name}</td>
                                        <td>{evidence.description}</td>
                                        <td>{evidence.deprecationDate}</td>
                                        <td>{showVersion(evidence.version)}</td>
                                        <td>{evidence.hypeLevel}</td>

                                        <td>
                                            <button className="btn btn-warning"
                                                    onClick={() => deleteEvidence(evidence.id)}>Delete
                                            </button>
                                        </td>
                                        <td>
                                            <button className="btn btn-success"
                                                    onClick={() => updateEvidence(evidence.id)}>Update
                                            </button>
                                        </td>
                                    </tr>
                                )
                            )
                        ) : (
                            <h1>No results found!</h1>
                        )
                    }
                    </tbody>

                </table>
            </div>
            <div className="btn btn-success m-5" onClick={addNewEvidence}>Add New Evidence</div>
        </div>
    )
}

export default ListEvidencesComponent