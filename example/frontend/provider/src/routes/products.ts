import { Router } from 'express';
import ProductController from '../presentation/controller/product-controller';

const router = Router();

router.get("/api/product/:id", ProductController.getById);
router.get("/api/products", ProductController.getAll);
router.post("/api/product", ProductController.store)
router.put("/api/product", ProductController.update)

export default router;