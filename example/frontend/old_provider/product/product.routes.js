const router = require("express").Router();
const controller = require("./product.controller");

router.get("/api/product/:id", controller.getById);
router.get("/api/products", controller.getAll);

module.exports = router;
