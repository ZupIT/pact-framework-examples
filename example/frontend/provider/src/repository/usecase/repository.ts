import { Product } from "../../domain/product";

export interface Repository {
  getAll(): Promise<Product[]>
  getById(id: number): Promise<Product>
  store(id: number, type: string, name: string): Promise<Product>
}
