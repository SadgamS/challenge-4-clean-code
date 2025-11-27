import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product.model';
import { ProductService } from '../services/product.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  imports: [CommonModule, FormsModule, RouterModule],
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
})
export class ProductFormComponent implements OnInit {
  model: Product = { name: '', description: '', price: 0, stock: 0 };
  isEdit = false;
  id?: number;

  constructor(private svc: ProductService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEdit = true;
      this.id = Number(idParam);
      this.svc.get(this.id).subscribe({
        next: (p) => (this.model = p),
        error: () => alert('No se encontró el producto'),
      });
    }
  }

  onSubmit() {
    if (this.isEdit && this.id) {
      this.svc.update(this.id, this.model).subscribe({
        next: () => this.router.navigate(['/']),
        error: () => alert('Error al actualizar'),
      });
    } else {
      this.svc.create(this.model).subscribe({
        next: () => this.router.navigate(['/']),
        error: () => alert('Error al crear'),
      });
    }
  }
}
