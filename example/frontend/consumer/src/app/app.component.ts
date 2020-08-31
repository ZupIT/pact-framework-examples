import { Component, OnInit } from '@angular/core';
import { Product } from './product.service';

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

  saveProduct() {
    if (this.isEditing) {
      const product = this.products.find((product) => product.id === this.id);

      product.name = this.name;
      product.type = this.type;
    } else {
      this.products.push({ id: this.id, name: this.name, type: this.type });
    }

    this.changeEdit(false);
    this.clearFields();
  }

  removeProduct(id: number) {
    this.products = this.products.filter((product) => product.id !== id);
  }

  changeEdit(value: boolean) {
    this.isEditing = value;
  }

  editProduct(id: number) {
    const product: Product = this.products.find((product) => product.id === id);

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

  ngOnInit() {
    this.isEditing = false;
    this.clearFields();
  }
}
