import { HttpResponse } from '@angular/common/http';
import {
  async,
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick,
} from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { Product, ProductService } from './product.service';

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
            delete: () => of({}),
          },
        },
      ],
      imports: [FormsModule],
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

  it('should list products on component init', () => {
    spyOn(service, 'getAll').and.returnValue(of(new HttpResponse()));

    app.ngOnInit();

    expect(service.getAll).toHaveBeenCalled();
  });

  it('should list all products', fakeAsync(() => {
    spyOn(service, 'getAll').and.returnValue(
      of(new HttpResponse({ body: [] }))
    );

    app.listAll();
    tick();

    expect(service.getAll).toHaveBeenCalled();
  }));

  it('should create a product', fakeAsync(() => {
    app.isEditing = false;
    spyOn(service, 'create').and.returnValue(of(new HttpResponse()));

    app.saveProduct();
    tick();

    expect(service.create).toHaveBeenCalled();
  }));

  it('should save a product that already exist', fakeAsync(() => {
    app.isEditing = true;
    spyOn(service, 'update').and.returnValue(of(new HttpResponse()));

    app.saveProduct();
    tick();

    expect(service.update).toHaveBeenCalled();
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
