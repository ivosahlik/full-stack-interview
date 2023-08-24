import {useEffect, useState} from 'react'
import {useNavigate, useParams} from 'react-router-dom'
import {createEvidenceApi, retrieveEvidenceApi, updateEvidenceApi} from './api/EvidenceApiService'
import {useAuth} from './security/AuthContext'
import {ErrorMessage, Field, FieldArray, Form, Formik} from 'formik'
import moment from 'moment'

export default function EvidenceComponent() {

    const {id} = useParams()

    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [deprecationDate, setDeprecationDate] = useState('')
    const [hypeLevel, setHypeLevel] = useState('')
    const [version, setVersion] = useState([])
    const [newVersion, setNewVersion] = useState([])

    const authContext = useAuth()
    const navigate = useNavigate()

    const username = authContext.username

    useEffect(() => retrieveEvidences(), [id])

    function retrieveEvidences() {
        if (id != -1) {
            retrieveEvidenceApi(username, id)
                .then(response => {
                    setName(response.data.name)
                    setDescription(response.data.description)
                    setDeprecationDate(response.data.deprecationDate)
                    setHypeLevel(response.data.hypeLevel)
                    setVersion(response.data.version)
                })
                .catch(error => console.log(error))
        }
    }

    function onSubmit(values) {
        if (values.newVersion !== '') {
            values.version.push(values.newVersion)
            setVersion(version)
        }

        const evidence = {
            id: id,
            name: values.name,
            description: values.description,
            version: values.version.filter(value => Object.keys(value).length !== 0),
            hypeLevel: values.hypeLevel,
            deprecationDate: values.deprecationDate
        }

        if (id == -1) {
            createEvidenceApi(evidence)
                .then(response => {
                    navigate('/evidences')
                })
                .catch(error => console.log(error))

        } else {
            updateEvidenceApi(id, evidence)
                .then(response => {
                    navigate('/evidences')
                })
                .catch(error => console.log(error))
        }
    }

    function validate(values) {
        let errors = {}

        if (values.name.length < 5) {
            errors.name = 'Enter atleast 5 characters to name'
        }

        if (values.description.length < 5) {
            errors.description = 'Enter atleast 5 characters to description'
        }

        if (values.deprecationDate == null || values.deprecationDate === '' || !moment(values.deprecationDate).isValid()) {
            errors.deprecationDate = 'Enter a target date'
        }

        if (values.hypeLevel.length < 1) {
            errors.hypeLevel = 'Enter atleast 1 characters to hype level'
        }

        return errors
    }

    function showVersion() {
        return (
            <div>
                {
                    version.map(version => {
                        return <span className="badge bg-secondary" style={{margin: 5 + 'px'}}
                                     onClick={() => deleteVersionById(version.id)}>{version.version}</span>
                    })
                }
            </div>
        );
    }

    function deleteVersionById(id) {
        const newVer = version.filter((ver) => ver.id !== id);
        version.pop()
        setVersion(newVer)
    }

    return (
        <div className="container">
            <h1>Enter Evidence Details </h1>
            <div>
                <Formik initialValues={{name, description, deprecationDate, hypeLevel, version, newVersion}}
                        enableReinitialize={true}
                        onSubmit={onSubmit}
                        validate={validate}
                        validateOnChange={false}
                        validateOnBlur={false}
                >
                    {
                        (props) => (
                            <Form>
                                <ErrorMessage
                                    name="name"
                                    component="div"
                                    className="alert alert-warning"
                                />

                                <ErrorMessage
                                    name="description"
                                    component="div"
                                    className="alert alert-warning"
                                />

                                <ErrorMessage
                                    name="targetDate"
                                    component="div"
                                    className="alert alert-warning"
                                />

                                <ErrorMessage
                                    name="hypeLevel"
                                    component="div"
                                    className="alert alert-warning"
                                />

                                <fieldset className="form-group">
                                    <label>Name</label>
                                    <Field type="text" className="form-control" name="name"/>
                                </fieldset>

                                <fieldset className="form-group">
                                    <label>Description</label>
                                    <Field type="text" className="form-control" name="description"/>
                                </fieldset>

                                <fieldset className="form-group">
                                    <label>Deprecation Date</label>
                                    <Field type="date" className="form-control" name="deprecationDate"/>
                                </fieldset>

                                <fieldset className="form-group">
                                    <label>Hype Level</label>
                                    <Field type="text" className="form-control" name="hypeLevel"/>
                                </fieldset>

                                <fieldset className="form-group">
                                    <label>Version</label>
                                    <Field type="text" className="form-control" name="newVersion"/>
                                </fieldset>

                                <fieldset className="form-group">
                                    <FieldArray
                                        component={showVersion}
                                    />
                                </fieldset>

                                <div>
                                    <button className="btn btn-success m-5" type="submit">Save</button>
                                </div>
                            </Form>
                        )
                    }
                </Formik>
            </div>

        </div>
    )
}