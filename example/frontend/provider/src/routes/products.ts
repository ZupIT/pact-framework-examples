import { Router, Request, Response } from 'express';
import { ProductController } from '../presentation/controller/product-controller';
import productRepository from '../repository/product/product-repository';

const productController = new ProductController(productRepository);
const router = Router();

router.get("/api/products/:id", async (req: Request, res: Response) => {
  const response = await productController.getById({ body: req.body, params: req.params })
  res.status(response.statusCode).json(response.body)
});

router.get("/api/products", async (req: Request, res: Response) => {
  const response = await productController.getAll();
  res.status(response.statusCode).json(response.body)
});

router.post("/api/products", async (req: Request, res: Response) => {
  const response = await productController.save({ body: req.body })
  res.status(response.statusCode).json(response.body)
})

router.put("/api/products", async (req: Request, res: Response) => {
  const response = await productController.update({ body: req.body, params: req.params })
  res.status(response.statusCode).json(response.body)
})

router.delete("/api/products/:id", async (req: Request, res: Response) => {
  const response = await productController.delete({ params: req.params })
  res.status(response.statusCode).json(response.body)
})

export default router;