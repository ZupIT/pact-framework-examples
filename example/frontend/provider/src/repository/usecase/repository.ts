import { Product } from "../../domain/product";

export interface Repository {
  getAll(): Promise<Product[]>
  getById(id: number): Promise<Product>
  store(id: number, type: string, name: string): Promise<Product>
  update(id: number, type: string, name: string): Promise<Product>
  delete(id: number): Promise<boolean>
}
