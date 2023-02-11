import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RadnikComponent } from '../list/radnik.component';
import { RadnikDetailComponent } from '../detail/radnik-detail.component';
import { RadnikUpdateComponent } from '../update/radnik-update.component';
import { RadnikRoutingResolveService } from './radnik-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const radnikRoute: Routes = [
  {
    path: '',
    component: RadnikComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RadnikDetailComponent,
    resolve: {
      radnik: RadnikRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RadnikUpdateComponent,
    resolve: {
      radnik: RadnikRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RadnikUpdateComponent,
    resolve: {
      radnik: RadnikRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(radnikRoute)],
  exports: [RouterModule],
})
export class RadnikRoutingModule {}
