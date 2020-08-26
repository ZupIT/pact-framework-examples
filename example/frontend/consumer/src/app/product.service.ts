import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface Product {
  id: number;
  type: string;
  name: string
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private BASE_URL = '/api/products';

  constructor(private httpClient: HttpClient) {}

  getAll() {
    return this.httpClient.get<HttpResponse<Product[]>>(this.BASE_URL, { observe: 'response' });
  }
}
