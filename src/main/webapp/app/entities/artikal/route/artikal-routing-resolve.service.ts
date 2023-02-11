import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IArtikal } from '../artikal.model';
import { ArtikalService } from '../service/artikal.service';

@Injectable({ providedIn: 'root' })
export class ArtikalRoutingResolveService implements Resolve<IArtikal | null> {
  constructor(protected service: ArtikalService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArtikal | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((artikal: HttpResponse<IArtikal>) => {
          if (artikal.body) {
            return of(artikal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
