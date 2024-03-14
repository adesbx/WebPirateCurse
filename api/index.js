import express from 'express'
import routes from './routes.js';
const app = express()
const port = 3000


app.use('/static', express.static('public'))

app.get('/', (req, res) => {
	res.redirect('/static')
})

routes(app);

app.listen(port, () => {
	console.log(`Example app listening on port ${port}`)
})

app.use((req, res) => {
	res.status(404).send("Sorry can't find that!")
})