import { Product } from "../../domain/product";

export interface Repository {
  getAll(): Promise<Product[]>
  getById(id: number): Promise<Product>
}
