import { HttpResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ProductService } from './product.service';
import { async, TestBed, fakeAsync, tick, ComponentFixture } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { Product } from './product.service';
import { of } from 'rxjs';

const PRODUCT_SAMPLE: Product = {
  id: 1,
  name: 'product',
  type: 'Type 1'
}

describe('AppComponent', () => {

  let fixture: ComponentFixture<AppComponent>;
  let service: ProductService;
  let app: AppComponent;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent],
      providers: [
        {
          provide: ProductService,
          useValue: {
            update: () => of({}),
            create: () => of({}),
            getAll: () => of({}),
            delete: () => of({})
          }
        }
      ],
      imports: [FormsModule]
    }).compileComponents();
  }));

  beforeEach(() => {
    service = TestBed.inject(ProductService);
    fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    app = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  it('should create a product', fakeAsync(() => {
    spyOn(service, 'getAll').and.returnValue(of(new HttpResponse({ body: [PRODUCT_SAMPLE] })));

    app.saveProduct();
    tick();

    expect(app.products.length).toEqual(1);
  }));

  it('should remove a product', fakeAsync(() => {
    spyOn(service, 'getAll').and.returnValue(of(new HttpResponse()));
    spyOn(service, 'delete').and.returnValue(of(new HttpResponse()));

    app.removeProduct(1);
    tick();

    expect(service.delete).toHaveBeenCalledWith(1);
    expect(service.getAll).toHaveBeenCalled();
    
  }));

  it('should edit a product', () => {
    const product: Product = { id: 1, name: 'name', type: 'type' };
    app.products.push(product);

    app.editProduct(product.id);

    expect(app.isEditing).toEqual(true);
    expect(app.id).toEqual(product.id);
    expect(app.name).toEqual(product.name);
    expect(app.type).toEqual(product.type);
  });

  it('should change editing value ', () => {
    app.changeEdit(true);

    expect(app.isEditing).toEqual(true);

    app.changeEdit(false);

    expect(app.isEditing).toEqual(false);
  });

  it('should cancel editing product', () => {
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
    app.id = 1;
    app.name = 'name';
    app.type = 'type';

    app.clearFields();

    expect(app.id).toEqual(null);
    expect(app.name).toEqual(null);
    expect(app.type).toEqual(null);
  });
});
