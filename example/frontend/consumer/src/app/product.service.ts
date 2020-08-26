import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private BASE_URL = '/product-service/products';

  constructor(private httpClient: HttpClient) {}

  getAllProducts(): Observable<object> {
    return this.httpClient.get(this.BASE_URL);
  }
}
