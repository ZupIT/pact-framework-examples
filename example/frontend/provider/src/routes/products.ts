import { Router } from 'express';
import { getAll, getById } from '../controller/product-controller';

const router = Router();

router.get("/api/product/:id", getById);
router.get("/api/products", getAll);

export default router;