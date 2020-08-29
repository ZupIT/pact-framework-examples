import { Router } from 'express';
import ProductController from '../presentation/controller/product-controller';

const router = Router();

router.get("/api/product/:id", ProductController.getById);
router.get("/api/products", ProductController.getAll);

export default router;