import express from 'express';
import cors from 'cors';
import routes from './routes/products';

const app = express();
const port = 3333;

app.use(cors())
app.use(routes);

app.listen(port, () => console.log(`Provider listening on port ${port}`))
