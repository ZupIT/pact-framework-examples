import { async, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { Product } from './product.service';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent],
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should create a product', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.id = 1;
    app.name = 'Product 1';
    app.type = 'Type 1';

    app.saveProduct();
    expect(app.products.length).toEqual(1);
  });

  it('should remove a product', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    const product: Product = { id: 1, name: 'name', type: 'type' };
    app.products.push(product);

    app.removeProduct(product.id);
    expect(app.products.length).toEqual(0);
  });

  it('should edit a product', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    const product: Product = { id: 1, name: 'name', type: 'type' };
    app.products.push(product);

    app.editProduct(product.id);

    expect(app.isEditing).toEqual(true);
    expect(app.id).toEqual(product.id);
    expect(app.name).toEqual(product.name);
    expect(app.type).toEqual(product.type);
  });

  it('should change editing value ', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.changeEdit(true);

    expect(app.isEditing).toEqual(true);

    app.changeEdit(false);

    expect(app.isEditing).toEqual(false);
  });

  it('should cancel editing product', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.isEditing = true;
    app.id = 1;
    app.name = 'name';
    app.type = 'type';

    app.cancel();

    expect(app.isEditing).toEqual(false);
    expect(app.id).toEqual(null);
    expect(app.name).toEqual(null);
    expect(app.type).toEqual(null);
  });

  it('should clear fields', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.id = 1;
    app.name = 'name';
    app.type = 'type';

    app.clearFields();

    expect(app.id).toEqual(null);
    expect(app.name).toEqual(null);
    expect(app.type).toEqual(null);
  });
});
