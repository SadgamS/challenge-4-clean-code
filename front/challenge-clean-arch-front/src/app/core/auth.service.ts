import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

interface TokenResp {
  accessToken: string;
  tokenType?: string;
  expiresIn?: number;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenKey = 'store_token';

  constructor(private http: HttpClient) {}
  
  login(username: string, password: string): Observable<void> {
    return this.http
      .post<TokenResp>(`${environment.apiUrl}/auth/token`, { username, password })
      .pipe(
        map((r) => {
          if (!r || !r.accessToken) throw new Error('Invalid token response');
          this.setToken(r.accessToken);
        })
      );
  }

  register(username: string, password: string) {
    return this.http.post<void>(`${environment.apiUrl}/auth/register`, { username, password });
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
  }

  setToken(token: string) {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
