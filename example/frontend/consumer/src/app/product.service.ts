import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface Product {
  id: number;
  type: string;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private BASE_URL = '/api/products';

  constructor(private httpClient: HttpClient) {}

  getAll() {
    return this.httpClient.get<Product[]>(this.BASE_URL, {
      observe: 'response',
    });
  }

  getOne(id: number) {
    return this.httpClient.get<Product>(`${this.BASE_URL}/${id}`, {
      observe: 'response',
    });
  }

  create(product: Product) {
    return this.httpClient.post<Product>(this.BASE_URL, product, {
      observe: 'response',
    });
  }

  update(id: number, product: Omit<Product, 'id'>) {
    return this.httpClient.put<Product>(`${this.BASE_URL}/${id}`, product, {
      observe: 'response',
    });
  }

  delete(id: number) {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${id}`, {
      observe: 'response',
    });
  }
}
