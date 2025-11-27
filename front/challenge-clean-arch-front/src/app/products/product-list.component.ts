import { Router, RouterModule } from '@angular/router';
import { Product } from '../models/product.model';
import { ProductService } from '../services/product.service';
import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  imports: [CommonModule, RouterModule],
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit {
  products: WritableSignal<Product[]> = signal<Product[]>([]);

  constructor(private svc: ProductService, private router: Router) {}

  ngOnInit() {
    this.reload();
  }

  reload() {
    this.svc.list().subscribe({
      next: (data) => this.products.set(data),
      error: () => alert('Error al obtener productos'),
    });
  }

  edit(p: Product) {
    this.router.navigate(['/products', p.id, 'edit']);
  }

  remove(p: Product) {
    if (!confirm('Eliminar producto?')) return;
    this.svc.delete(p.id!).subscribe({
      next: () => this.reload(),
      error: () => alert('Error al eliminar'),
    });
  }
}
