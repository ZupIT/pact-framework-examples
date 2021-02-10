import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';

export interface Product {
  id: number;
  type: string;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private BASE_URL = `${environment.BASE_URL}/products`;

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

  update(product: Product) {
    return this.httpClient.put<Product>(
      `${this.BASE_URL}/${product.id}`,
      { name: product.name, type: product.type },
      {
        observe: 'response',
      }
    );
  }

  delete(id: number) {
    return this.httpClient.delete<void>(`${this.BASE_URL}/${id}`, {
      observe: 'response',
    });
  }
}
