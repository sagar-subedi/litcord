import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { AuthService } from "../services/auth.service";

export function tokenIterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) : Observable<HttpEvent<unknown>> {
    // Inject the current `AuthService` and use it to get an authentication token:
    const authToken = inject(AuthService).getToken();
    // Clone the request to add the authentication header.
    
    if(authToken){
        const newReq = req.clone({
          headers: req.headers.append('Authorization', `Bearer ${authToken}`),
        });
        return next(newReq);
    }
    return next(req);
}