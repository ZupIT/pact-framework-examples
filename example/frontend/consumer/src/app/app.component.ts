import { Component, OnInit } from '@angular/core';
import { Product, ProductService } from './product.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  products: Product[] = [];

  isEditing: boolean;

  id: number;
  name: string;
  type: string;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.listAll().then();
    this.isEditing = false;
    this.clearFields();
  }

  async saveProduct() {
    if (this.isEditing) {
      await this.productService
        .update({ id: this.id, name: this.name, type: this.type })
        .toPromise();
    } else {
      await this.productService
        .create({
          id: this.id,
          name: this.name,
          type: this.type,
        })
        .toPromise();
    }

    await this.listAll();
    this.changeEdit(false);
    this.clearFields();
  }

  async listAll() {
    return this.productService
      .getAll()
      .toPromise()
      .then((res) => (this.products = res.body));
  }

  async removeProduct(id: number) {
    await this.productService.delete(id).toPromise();
    await this.listAll();
  }

  changeEdit(value: boolean) {
    this.isEditing = value;
  }

  async editProduct(id: number) {
    let product: Product = this.products.find((product) => product.id === id);

    this.changeEdit(true);
    this.id = id;
    this.name = product.name;
    this.type = product.type;
  }

  cancel() {
    this.changeEdit(false);
    this.clearFields();
  }

  clearFields() {
    this.id = null;
    this.name = null;
    this.type = null;
  }
}
