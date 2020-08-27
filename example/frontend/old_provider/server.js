const express = require("express");
const cors = require("cors");
const routes = require("./product/product.routes");
const authMiddleware = require("./middleware/auth.middleware");

const app = express();
const port = 3333;

app.use(cors());
app.use(routes);
app.use(authMiddleware);
app.listen(port, () => console.log(`Provider listening on port ${port}`));
