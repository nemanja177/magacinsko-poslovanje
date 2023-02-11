import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArtikalComponent } from '../list/artikal.component';
import { ArtikalDetailComponent } from '../detail/artikal-detail.component';
import { ArtikalUpdateComponent } from '../update/artikal-update.component';
import { ArtikalRoutingResolveService } from './artikal-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const artikalRoute: Routes = [
  {
    path: '',
    component: ArtikalComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArtikalDetailComponent,
    resolve: {
      artikal: ArtikalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArtikalUpdateComponent,
    resolve: {
      artikal: ArtikalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArtikalUpdateComponent,
    resolve: {
      artikal: ArtikalRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(artikalRoute)],
  exports: [RouterModule],
})
export class ArtikalRoutingModule {}
