import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private base = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) {}

  list(): Observable<Product[]> {
    return this.http.get<Product[]>(this.base);
  }

  get(id: number) {
    return this.http.get<Product>(`${this.base}/${id}`);
  }

  create(p: Product) {
    return this.http.post<Product>(this.base, p);
  }

  update(id: number, p: Product) {
    return this.http.put<Product>(`${this.base}/${id}`, p);
  }

  delete(id: number) {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
